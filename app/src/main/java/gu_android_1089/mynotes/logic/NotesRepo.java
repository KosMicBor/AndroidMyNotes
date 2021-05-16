package gu_android_1089.mynotes.logic;

import java.util.List;

public class NotesRepo implements NotesRepoInterface {
    @Override
    public void getNotes(CallbackInterface<List<Note>> callback) {

    }

    @Override
    public void addNote(String title, String noteText, CallbackInterface<Note> newNote) {

    }

    @Override
    public void editNote(Note note, CallbackInterface<Note> editNoteCallback) {

    }

    @Override
    public void deleteNote(Note note, CallbackInterface<Note> removeNoteCallback) {

    }

    /*private ArrayList<Notes> notes = new ArrayList<>();

    @Override
    public List<Notes> getNotes() {

        notes.add(new Notes(1d, "Покупки", "Купить молоко", "Стандартные"));
        notes.add(new Notes(2d, "TODO", "Выкомпать червяка", "Стандартные"));
        notes.add(new Notes(3d, "TODO", "Накормить червяком соседа", "Стандартные"));
        notes.add(new Notes(4d, "Ещё заметка", "Ещё какой-то текст", "Стандартные"));
        notes.add(new Notes(5d, "Ещё заметка", "И опять какой-то текст", "Очень важные"));
        notes.add(new Notes(6d, "Вы не поверите", "Тут текст для удивления", "Личные"));
        notes.add(new Notes(7d, "Тестовая", "Тут текст для теста", "Личные"));
        notes.add(new Notes(8d, "Главная цель", "Захватить мир", "Личные"));
        notes.add(new Notes(9d, "Заметка", "Да, просто заметка", "Личные"));
        notes.add(new Notes(10d, "Lorem Ipsum", "Жаль, что не работает", "Личные"));
        notes.add(new Notes(11d, "Кончилась фантазия", "Печаль", "Личные"));
        notes.add(new Notes(12d, "А тут тем более", "Для продолжения, нажмите себе на наос", "Личные"));
        return notes;
    }

    @Override
    public void addNote (Notes note){
        notes.add(note);
    }

    @Override
    public void editNote (int position, Notes note){
        notes.set(position, note);
    }

    @Override
    public void deleteNote(int position) {
        notes.remove(position);
    }*/


}
