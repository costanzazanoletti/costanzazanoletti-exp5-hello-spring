package org.lessons.hellospring.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // dice a Spring che questa classe è un Controller
@RequestMapping("/") // a quale rotta rispondono i metodi di questo Controller
public class IndexController {

  @GetMapping // risponde a richieste di tipo HTTP GET
  @ResponseBody // mette la stringa restituita direttamente nel body della response
  public String home(){
    return """
        <html>
          <head>
            <title>Hello</title>
          </head>
          <body>
            <h1>Hello Spring</h1>
            <h2>
              Sono un html statico in response body
             </h2>
          </body>
        </html>
        """;
  }

  @GetMapping("/template")
  public String template(Model model){ // chiediamo a Spring di invocare questo metodo passandogli il Model
    String formattedNow = getFormattedNow();
    model.addAttribute("currentDateTime", formattedNow); // aggiungo alla mappa del Model un attributo
    return "index"; // ritorno il nome del template index.html che si trova nella cartella resources/templates
  }


  private String getFormattedNow(){
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    return now.format(formatter);
  }
}
