package com.example.githubtest.utils

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

//class HttpHelper {
//
//
//}
//
//// HttpClient(CIO)
//val httpClient = HttpClient {
//    install(ContentNegotiation) {
//        json(
//            Json {
//                prettyPrint = true
//                isLenient = true
//                ignoreUnknownKeys = true
//            }
//        )
//    }
//    install(Logging) {
//        logger = Logger.DEFAULT
//        level = LogLevel.ALL
////        filter { request ->
////            request.url.host.contains("ktor.io")
////        }
//    }
//    engine {
//        // this: HttpClientEngineConfig
//        threadsCount = 4
//        pipelining = true
//    }
//}