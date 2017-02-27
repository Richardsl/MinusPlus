package sl.richard.app.MinusPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Richard on 31.05.2016.
 */
public class QuestionSystem {

    String questionText;
    int answer;

    List <Integer> answers;

    //----------------

    public QuestionSystem(){
        answers = new ArrayList<Integer>();
    }

    //---public---
    public String getQuestion(){
        return this.questionText;
    }
    public int getAnswer(){
        return this.answer;
    }

    public List getAnswers(){
        return answers;
    }

    public void generateQuestion(){

        Random r = new Random();
        int mathType = r.nextInt(2);

        int number1 = r.nextInt(19 - 11 + 1) + 11;
        int number2 = r.nextInt(5 - 1 + 1) + 1;

        //gen correct answer and question
        if(mathType==0){
            answer = number1 + number2;
            questionText = number1 + " + " + number2 + "= ";

        }else{
            answer = number1 - number2;
            questionText = number1 + " - " + number2 + "= ";
        }

        //gen 3 false answers
        answers.clear();

        int otherNumber = r.nextInt(4 - 1 + 1) + 1;
        answers.add(answer);
        answers.add(answer+otherNumber);
        answers.add(answer-otherNumber);
        answers.add(answer+otherNumber+5);



        //Log.i("r89-arr 0", answers.toString());
        /*Log.i("r89-arr 1", answers[1].toString());
        Log.i("r89-arr 2", answers[2].toString());
        Log.i("r89-arr 3", answers[3].toString());*/
        //Log.i("r89-math var: ", Integer.toString(number2));
        //int mathType = r.nextInt(4 - 0 + 1) + 0;
    }

}
