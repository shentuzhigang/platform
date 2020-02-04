package club.zstuca.platform.controller;

import org.springframework.boot.web.servlet.error.ErrorController;

public class CustomizeErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return null;
    }
}
