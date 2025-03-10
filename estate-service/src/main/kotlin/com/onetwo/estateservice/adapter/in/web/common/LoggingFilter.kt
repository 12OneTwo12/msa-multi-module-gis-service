package com.onetwo.estateservice.adapter.`in`.web.common

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import java.io.InputStream
import java.util.*

@Component
class LoggingFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        MDC.put("traceId", UUID.randomUUID().toString())

        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response)
        } else {
            doFilterWrapped(
                RequestWrapper(request),
                ResponseWrapper(response), filterChain
            )
        }
        MDC.clear()
    }

    @Throws(ServletException::class, IOException::class)
    fun doFilterWrapped(
        request: HttpServletRequest,
        response: ContentCachingResponseWrapper,
        filterChain: FilterChain
    ) {
        val start = System.currentTimeMillis()
        try {
            logRequest(request)
            filterChain.doFilter(request, response)
        } finally {
            val end = System.currentTimeMillis()
            val workTime = end - start
            logResponse(response, workTime)
            response.copyBodyToResponse()
        }
    }

    @Throws(IOException::class)
    fun logRequest(request: HttpServletRequest) {
        val queryString = request.queryString

        logger.info(
            "Request : ${request.method} " +
                    "uri=[${request.remoteAddr}] " +
                    "request-ip=[${if (queryString == null) request.requestURI else request.requestURI + "?" + queryString}] " +
                    "header=[${getHeaders(request)}] " +
                    "content-type=[${request.contentType}] "
        )

        logRequestPayload("Request", request.contentType, request.inputStream)
    }

    @Throws(IOException::class)
    fun logResponse(response: ContentCachingResponseWrapper, workTime: Long) {
        logResponsePayload("Response", response.contentType, response.contentInputStream, workTime)
    }

    @Throws(IOException::class)
    fun logRequestPayload(prefix: String, contentType: String?, inputStream: InputStream) {
        val visible: Boolean = isVisible(MediaType.valueOf(contentType ?: "application/json"))
        if (visible) {
            val content = StreamUtils.copyToByteArray(inputStream)
            if (content.isNotEmpty()) {
                val contentString = String(content)
                logger.info(
                    "$prefix Payload: ${
                        contentString.replace(
                            System.lineSeparator().toRegex(),
                            ""
                        )
                    }",
                )
            }
        } else {
            logger.info("{${prefix}} Payload: Binary Content")
        }
    }

    @Throws(IOException::class)
    fun logResponsePayload(prefix: String, contentType: String?, inputStream: InputStream, workTime: Long) {
        val visible: Boolean = isVisible(MediaType.valueOf(contentType ?: "application/json"))
        if (visible) {
            val content = StreamUtils.copyToByteArray(inputStream)
            if (content.isNotEmpty()) {
                val contentString = String(content)
                logger.info("$prefix Response Payload: $contentString (${workTime}ms)")
            }
        } else {
            logger.info("$prefix Response Payload: Binary Content (${workTime}ms)")
        }
    }

    fun isVisible(mediaType: MediaType): Boolean {
        val visibleType = listOf(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
        )

        return visibleType.stream()
            .anyMatch {
                it.includes(
                    mediaType
                )
            }
    }


    fun getHeaders(request: HttpServletRequest): Map<String, Any> {
        val headerMap: MutableMap<String, Any> = HashMap()

        val headerArray = request.headerNames
        while (headerArray.hasMoreElements()) {
            val headerName = headerArray.nextElement()
            headerMap[headerName] = request.getHeader(headerName)
        }
        return headerMap
    }
}