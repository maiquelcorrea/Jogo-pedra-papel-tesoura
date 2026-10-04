
import java.util.List;
import java.util.Set;
import java.util.Random;


public class App {

    public static void main(String[] args) {
        Hand firstHand = new Hand(JokenpoService.getRandomGesture());
        Hand secondHand = new Hand(JokenpoService.getRandomGesture());
        Result result = JokenpoService.evaluateMatch(firstHand, secondHand);
        String contextualizedResult = JokenpoService.contextualizeResult(result);
        System.out.println(
            firstHand.gesture().name()
            + contextualizedResult +
            secondHand.gesture().name()
        );
    }
}


// ~ S E R V I C E

abstract class JokenpoService {

    private static final Random RANDOM = new Random();

    private static final String ROCK = "Pedra";
    private static final String PAPER = "Papel";
    private static final String SCISSORS = "Tesoura";
    
    private  static final Gesture ROCK_GESTURE = new Gesture(ROCK, SCISSORS, PAPER);
    private  static final Gesture PAPER_GESTURE = new Gesture(PAPER, ROCK, SCISSORS);
    private static final Gesture SCISSORS_GESTURE = new Gesture(SCISSORS, PAPER, ROCK);
    private static final List<Gesture> GESTURES = List.of(
        ROCK_GESTURE,
        PAPER_GESTURE,
        SCISSORS_GESTURE
    );

    public static Gesture getRandomGesture() {
        int index = RANDOM.nextInt(GESTURES.size());
        return GESTURES.get(index);
    }

    public static Result evaluateMatch(Hand firstHand, Hand secondHand) {
        boolean wins = firstHand.gesture().winsAgainst().equals(
            secondHand.gesture().name()
        );
        if (wins) return Result.WINS;

        boolean loses = firstHand.gesture().losesAgainst().equals(
            secondHand.gesture().name()
        );
        if (loses) return Result.LOSES;

        return Result.DRAWS;
    }

    public static String contextualizeResult(Result result) {
        String contextualizedResult = switch(result) {
            case WINS -> "vence contra";
            case LOSES -> "perde contra";
            case DRAWS -> "empata com";
        };
        return " " + contextualizedResult + " ";
    }
}


// ~ M O D E L

enum Result { WINS, LOSES, DRAWS }

record Hand(Gesture gesture) {}

record Gesture(String name, String winsAgainst, String losesAgainst) {}