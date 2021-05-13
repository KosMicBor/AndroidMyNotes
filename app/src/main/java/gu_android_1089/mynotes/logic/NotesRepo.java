package gu_android_1089.mynotes.logic;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NotesRepo {

    public List<Notes> getNotes() {
        ArrayList<Notes> notes = new ArrayList<>();
        //на данном этапе есть предустановленные заметки для демонстрации, в будущем будет
        //реализован функционал добавления новых заметок через параметры метода
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
}
