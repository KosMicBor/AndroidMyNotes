package gu_android_1089.mynotes.ui;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gu_android_1089.mynotes.R;
import gu_android_1089.mynotes.logic.Note;
import gu_android_1089.mynotes.logic.OnNoteClickListener;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder>
        implements OnNoteClickListener {



    private ArrayList<Note> notes = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;
    private final Fragment fragment;
    private int selectedItemPosition;

    public void setNotes(List<Note> noteList) {
        notes.clear();
        notes.addAll(noteList);
        notifyDataSetChanged();
    }

    public void addNewNoteRefreshList(List<Note> newNotes){
        notes.clear();
        notes = (ArrayList<Note>) newNotes;
    }

    public void editNewNote(List<Note> newNotes){
        notes.clear();
        notes = (ArrayList<Note>) newNotes;
        notifyDataSetChanged();
    }

    public void removeNote(int position){
        notes.remove(position);
        notifyItemRemoved(position);
    }


    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    public MainListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.note_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notes_title_in_list);

            if (fragment != null) {
                fragment.registerForContextMenu(itemView);
            }
        }

        public void bind(Note note) {
            title.setText(note.getTitle());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.itemView.setOnClickListener(v -> {
            onNoteClickListener = (OnNoteClickListener) holder.itemView.getContext();
            onNoteClickListener(notes.get(position), position);

        });

        holder.itemView.setOnLongClickListener(v -> {
            holder.itemView.showContextMenu(10, 10);
            selectedItemPosition = holder.getLayoutPosition();
            return true;
        });
    }

    @Override
    public void onNoteClickListener(Note note, int position) {
        onNoteClickListener.onNoteClickListener(note, position);
    }

    Note getNote (int position){
        return notes.get(position);
    }
}
