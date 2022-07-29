package fdr.home.task.web.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "UNAUTHORIZED") // 401
class UnAuthorizedException : IllegalAccessException() {}