package com.carcharodon80.sweater.controller;

import com.carcharodon80.sweater.domain.Message;
import com.carcharodon80.sweater.domain.User;
import com.carcharodon80.sweater.repos.MessageRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    private MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String main() {
        return "home";
    }

    /**
     * Метод запускается, когда приходит Get-запрос на "messages" из браузера
     * @param model - модель, куда передаются сведения для отображения страницы ("messages" понадобится на "messages.html")
     * @param filter - фильтр тэгов
     * @return - имя страницы из resources/templates
     */
    @GetMapping("/messages")
    public String showMessages(@RequestParam (required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
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
    public String addMessage(@AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Model model) {
        Message message = new Message(text, tag, user);
        messageRepository.save(message);
        return "redirect:messages";
    }
}
