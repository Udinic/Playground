package com.udinic.general_testing;

import android.*;
import android.app.Activity;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import javax.mail.Folder;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 02/06/13
 * Time: 12:10
 */
public class ContentProviderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(populateTasks());
    }

    private View populateCategories() {
        Cursor curCat = getContentResolver().query(TasksContract.FOLDERS_URI, null, TasksContract.FolderColumns.IS_DEFAULT + " = ?", new String[]{"1"}, null);

        CursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item_task, curCat,
                new String[] {TasksContract.FolderColumns.NAME, TasksContract.FolderColumns.IS_DEFAULT},
                new int[]{R.id.title, R.id.category}, 0);

        ListView list = new ListView(this);
        list.setAdapter(adapter);

        return list;
    }

    private View populateTasks() {
        String[] projectionTasks = new String[]{TasksContract.TasksColumns.ID, TasksContract.TasksColumns.TITLE, TasksContract.TasksColumns.STATUS};
//        String[] projectionNotes = new String[]{TasksContract.NotesColumns.ID, TasksContract.NotesColumns.TITLE, TasksContract.NotesColumns.STATUS, TasksContract.NotesColumns.PARENT_ID};

        //TasksContract.TasksColumns.ID + "=?", new String[]{"1"}
        //TasksContract.TasksColumns.STATUS + " = 3"
//        Cursor tasks = getContentResolver().query(TasksContract.getTaskNotesUri(1), null, TasksContract.NotesColumns.STATUS + "="+TasksContract.STATUS_UNCHECKED, null, null);
        String[] projNotes = new String[]{TasksContract.NotesColumns.TITLE, TasksContract.NotesColumns.PARENT_ID};

        String[] projTasks = new String[]{TasksContract.TasksColumns.ID,
                                            TasksContract.TasksColumns.TITLE,
                                            TasksContract.TasksColumns.DUE_DATE,
                                            TasksContract.TasksColumns.STATUS,
                                            TasksContract.TasksColumns.PRIORITY};

        Cursor tasks = getContentResolver().query(TasksContract.TASKS_URI, projTasks,
                            TasksContract.TasksColumns.STATUS + "=?",
                            new String[] {String.valueOf(TasksContract.STATUS_UNCHECKED)}, null, null);

        CursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item_task, tasks,
                new String[] {TasksContract.TasksColumns.TITLE, TasksContract.TasksColumns.DUE_DATE},
                                        new int[]{R.id.title, R.id.category}, 0);

        ListView list = new ListView(this);
        list.setAdapter(adapter);

        return list;
    }
}
