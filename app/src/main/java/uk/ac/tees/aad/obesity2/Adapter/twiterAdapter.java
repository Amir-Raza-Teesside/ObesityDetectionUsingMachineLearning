package uk.ac.tees.aad.obesity2.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uk.ac.tees.aad.obesity2.Model.TweetModel;
import uk.ac.tees.aad.obesity2.R;

public class twiterAdapter extends RecyclerView.Adapter<twiterAdapter.viewholder> {

    private ArrayList<TweetModel> model;
    private Context context;

    public twiterAdapter(ArrayList<TweetModel> model, Context context) {
        this.model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_share,parent,false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        try{
        holder.name.setText(model.get(position).getAuthorname());
        holder.email.setText(model.get(position).getEmail());
        holder.tweet.setText(model.get(position).getTweet());
        holder.likeCounter.setText(model.get(position).getLikeCounter());
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }













    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{


        TextView name,email,tweet,likeCounter;
        Button button;

        public viewholder(@NonNull View itemView) {

            super(itemView);
            likeCounter = itemView.findViewById(R.id.likeCount);
            tweet = itemView.findViewById(R.id.tweet);
            name = itemView.findViewById(R.id.single_row_name);
            email = itemView.findViewById(R.id.single_row_email);
            button = itemView.findViewById(R.id.counter);



        }
    }
}
