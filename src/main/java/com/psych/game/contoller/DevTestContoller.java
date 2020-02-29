package com.psych.game.contoller;

import com.psych.game.model.*;
import com.psych.game.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dev-test")
public class DevTestContoller {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContentWriterRepository contentWriterRepository ;

    @Autowired
    private RoundRepository roundRepository ;

    @GetMapping("/")
    public String hello(){
        return "Hello Subham! Its Heroku site deployment working fine";
    }


    @GetMapping("/populate")
    public String populateDB(){

        for(Player player:playerRepository.findAll()){
            player.getGames().clear();
            playerRepository.save(player);
        }

        gameRepository.deleteAll();
        playerRepository.deleteAll();
        questionRepository.deleteAll();
        contentWriterRepository.deleteAll();

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

        Game game = new Game();
        game.setGameMode(GameMode.IS_THIS_A_FACT);
        game.setLeader(luffy);
        game.getPlayers().add(luffy);
        gameRepository.save(game);

        Question question_1 =  new Question(
                "What is the most important poneglyph",
                "Rio Poneglyph",
                GameMode.IS_THIS_A_FACT
        );

        Question question_2 = new Question(
                "How far can Luffy stretch?",
                "56 Gomu Gomus",
                GameMode.IS_THIS_A_FACT
        );

        questionRepository.save(question_1);
        questionRepository.save(question_2);

        ContentWriter contentWriter_1 = new ContentWriter.ContentWriterBuilder()
                .address("address_content_writer_1")
                .email("email_content_writer_1@abc.com")
                .name("name_content_writer_1")
                .phoneNumber("phone_number_content_writer_1")
                .saltedHashedPassword("password_content_writer_1")
                .build();
        ContentWriter contentWriter_2 = new ContentWriter.ContentWriterBuilder()
                .address("address_content_writer_2")
                .email("email_content_writer_2@abc.com")
                .name("name_content_writer_2")
                .phoneNumber("phone_number_content_writer_2")
                .saltedHashedPassword("password_content_writer_2")
                .build();

        contentWriterRepository.save(contentWriter_1);
        contentWriterRepository.save(contentWriter_2);





        return "Populated";


    }

    @GetMapping("/questions")
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    @GetMapping("/question/{id}")
    public Question getQuestionById(@PathVariable(name = "id") Long id){
        return questionRepository.findById(id).orElseThrow();
    }

    // PLAYERS

    @GetMapping("/players")
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping("/player/{id}")
    public Player getPlayerById(@PathVariable(name = "id") Long id){
        return playerRepository.findById(id).orElseThrow();
    }

    // USERS

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable(name = "id") Long id) {
        return userRepository.findById(id).orElseThrow();
    }


    // GAMES

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/game/{id}")
    public Game getGameById(@PathVariable(name = "id") Long id) {
        return gameRepository.findById(id).orElseThrow();
    }

    // Content writer

    @GetMapping("/contentWriters")
    public List<ContentWriter> getAllContentWriters() {
        return contentWriterRepository.findAll();
    }

    @GetMapping("/contentWriter/{id}")
    public ContentWriter getContentWriterById(@PathVariable(name = "id") Long id) {
        return contentWriterRepository.findById(id).orElseThrow();
    }

    // ROUNDS

    @GetMapping("/rounds")
    public List<Round> getAllRounds() {
        return roundRepository.findAll();
    }

    @GetMapping("/round/{id}")
    public Round getRoundById(@PathVariable(name = "id") Long id) {
        return roundRepository.findById(id).orElseThrow();
    }


    // admins rounds
}
