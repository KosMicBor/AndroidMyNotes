package gu_android_1089.mynotes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Notes;
import gu_android_1089.mynotes.logic.NotesRepo;
import gu_android_1089.mynotes.logic.OnNoteClickListener;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder>
        implements OnNoteClickListener {

    List<Notes> notes = new NotesRepo().getNotes();
    private OnNoteClickListener onNoteClickListener;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.note_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(notes.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notes_title_in_list);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.itemView.setOnClickListener(v -> {
            onNoteClickListener = (OnNoteClickListener) holder.itemView.getContext();
            onNoteClickListener(notes.get(position));
        });
    }

    @Override
    public void onNoteClickListener(Notes note) {
        onNoteClickListener.onNoteClickListener(note);
    }
}
