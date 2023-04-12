package com.carcharodon80.sweater.controller;

import com.carcharodon80.sweater.domain.Message;
import com.carcharodon80.sweater.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String main() {
        return "home";
    }

    /**
     * Метод запускается, когда приходит Get-запрос на "messages" из браузера
     * @param model - модель, куда передаются сведения для отображения страницы ("messages" понадобится на "messages.html")
     * @return - имя страницы из resources/templates
     */
    @GetMapping("/messages")
    public String showMessages(Model model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "messages";
    }

    /**
     * Метод запускается, когда приходит post-запрос на "addMessage" (свойство action у кнопки в messages.html)
     * @param text - см. кнопку в messages.html
     * @param tag - см. кнопку в messages.html
     * @param model - модель для передачи сведений на страницу
     * @return - здесь redirect, чтобы при обновлении страницы не происходило повторное добавление сообщения в БД
     */
    @PostMapping("/addMessage")
    public String addMessage(@RequestParam String text, @RequestParam String tag, Model model) {
        Message message = new Message(text, tag);
        messageRepository.save(message);
        return "redirect:messages";
    }

    /**
     * Метод запускается, когда приходит post-запрос на "filter" (свойство action у кнопки в messages.html)
     * @param filter - см. кнопку в messages.html
     * @param model - модель для передачи сведений на страницу
     * @return - отображение messages.html
     */
    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        return "messages";
    }
}
