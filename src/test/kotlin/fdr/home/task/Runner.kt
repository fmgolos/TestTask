package fdr.home.task

import mu.KLogging
import org.springframework.boot.SpringApplication

class Runner {

    companion object : KLogging() {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication(Application::class.java).run(*args)
        }
    }
}