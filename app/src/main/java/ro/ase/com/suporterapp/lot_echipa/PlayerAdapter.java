package ro.ase.com.suporterapp.lot_echipa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ro.ase.com.suporterapp.R;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private Context context;
    private List<Player> playerList;

    public PlayerAdapter(Context context, List<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lot_echipa_player_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.nameTextView.setText(player.getName());
        holder.detailsTextView.setText("Position: " + player.getPosition() + ", Age: " + player.getAge());
        Glide.with(context).load(player.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView detailsTextView;
        ImageView imageView;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_player_name);
            detailsTextView = itemView.findViewById(R.id.text_player_details);
            imageView = itemView.findViewById(R.id.player_image);
        }
    }
}
