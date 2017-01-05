package sav.CagedWisdom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

import sav.honouringcage.R;

public class QuestionListFragment extends Fragment {


    private RecyclerView mQuestionRecyclerView;
    private QuestionAdapter mAdapter;
    private Question mQuestion;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question_list, container, false);

        mQuestionRecyclerView = (RecyclerView) view
                .findViewById(R.id.question_recycler_view);
        mQuestionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        context = view.getContext();

        return view;


    }

    private void updateUI(){
        QuestionDatabase db=new QuestionDatabase(this.getContext());
        List<Question> questions = db.getQuestions();

        mAdapter = new QuestionAdapter(questions);
        mQuestionRecyclerView.setAdapter(mAdapter);
    }

    private class QuestionHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mQuestionNumber;
        private TextView mQuestionAnswer;

        public QuestionHolder(View itemView){
            super(itemView);
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_question_title_text_view);
            mQuestionNumber = (TextView)
                    itemView.findViewById(R.id.question_number);
            mQuestionAnswer = (TextView)
                    itemView.findViewById(R.id.question_answer);
        }

        public void bindQuestion(Question question){

            final String answer;

            mQuestion = question;
                        switch (mQuestion.getAnswerTrue()){
                case 1: answer = "True";
                        break;
                case 0: answer = "False";
                        break;
                default:answer=mQuestion.getAnswer();
            }
            mTitleTextView.setText(mQuestion.getQuestion());
            mQuestionNumber.setText("Question " + mQuestion.getTextResId());

            mQuestionAnswer.setText("Reveal Answer");
            mQuestionAnswer.setTextColor(Color.RED);
            mQuestionAnswer.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mQuestionAnswer.setText(answer);
                    mQuestionAnswer.setTextColor(Color.GRAY);
                }
            });



        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {
        private List<Question> mQuestions;
        public QuestionAdapter(List<Question> questions){
            mQuestions = questions;
        }

        @Override
        public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_question,parent,false);
           return new QuestionHolder(view);
        }

        @Override
        public void onBindViewHolder(QuestionHolder holder, int position){
            Question question = mQuestions.get(position);
            holder.bindQuestion(question);
        }


        @Override
        public int getItemCount(){
            return mQuestions.size();
        }
    }
}
