package fdr.home.task


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
//class Application {
//    fun main(args: Array<String>) {
//        runApplication<Application>(*args)
//    }
//}

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication(Application::class.java).run(*args)
        }
    }
}