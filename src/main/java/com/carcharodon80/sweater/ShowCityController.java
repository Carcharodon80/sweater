package com.carcharodon80.sweater;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShowCityController {

    /**
     * Браузер переходит по @GetMapping("/showName") и запускает метод showName,
     * @param city - параметр запроса, описывается аннотацией @RequestParam (имя, необходимость, дефолтное значение)
     *             можно задать в браузере, например http://localhost:8080/showName?text=Moscow
     * @param model - модель, куда передаются сведения для отображения страницы ("city" понадобится для <p th:text="${city}"></p>
     * @return - имя страницы из resources/templates
     */
    @GetMapping("/showName")
    public String showName(@RequestParam(name = "text", required = false, defaultValue = "Paris") String city, Model model) {
        model.addAttribute("city", city);
        return "showCity.html";
    }
}
