package com.psych.game;

import com.psych.game.model.GameMode;
import com.psych.game.model.Player;
import com.psych.game.model.Question;
import com.psych.game.repositories.PlayerRepository;
import com.psych.game.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dev-test")
public class HelloWorldController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public String hello(){
        return "Hello Subham! Its Heroku site deployment working fine";
    }


    @GetMapping("/populate")
    public String populateDB(){
        Player luffy = new Player.Builder()
                .alias("Monkey D. Luffy")
                .email("luffy@abc.com")
                .saltedHashedPassword("strawhat")
                .build();
        Player robin = new Player.Builder()
                .alias("Nico robin")
                .email("robin@abc.com")
                .saltedHashedPassword("poneglyph")
                .build();

        playerRepository.save(luffy);
        playerRepository.save(robin);

        questionRepository.save(new Question(
                "What is the most important poneglyph",
                "Rio Poneglyph",
                GameMode.IS_THIS_A_FACT));

        return "Populated";


    }



    @GetMapping("/questions")
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    @GetMapping("/question/{id}")
    public Question getQuestionById(@PathVariable(name = "id") Long Id){
        return questionRepository.findById(Id).orElseThrow();
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping("/player/{id}")
    public Player getPlayerById(@PathVariable(name = "id") Long Id){
        return playerRepository.findById(Id).orElseThrow();
    }

    //Games
    //players admins questions rounds content writers
}
