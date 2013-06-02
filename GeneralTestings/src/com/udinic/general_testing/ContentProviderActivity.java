package com.udinic.general_testing;

import android.*;
import android.app.Activity;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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

//        String[] projectionTasks = new String[]{TasksContract.TasksColumns.ID, TasksContract.TasksColumns.TITLE, TasksContract.TasksColumns.STATUS};
//        String[] projectionNotes = new String[]{TasksContract.NotesColumns.ID, TasksContract.NotesColumns.TITLE, TasksContract.NotesColumns.PARENT_ID};

        //TasksContract.TasksColumns.ID + "=?", new String[]{"1"}
        //TasksContract.TasksColumns.STATUS + " = 3"
        Cursor tasks = getContentResolver().query(Uri.parse("content://com.anydo.provider/tasks/2/notes"), null, null, null, null);

        String[] projTasks = new String[]{TasksContract.TasksColumns.TITLE, TasksContract.TasksColumns.STATUS};
        String[] projNotes = new String[]{TasksContract.NotesColumns.TITLE, TasksContract.NotesColumns.PARENT_ID};
        CursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item_task, tasks, projTasks,
                                        new int[]{R.id.title, R.id.category}, 0);

        ListView list = new ListView(this);
        list.setAdapter(adapter);

        setContentView(list);
    }

private static class TasksContract {
    public static final Uri TASKS_URI = Uri.parse("content://com.anydo.provider/tasks");

    public static final String PERMISSION_READ = "com.anydo.provider.permission.READ_ANYDO_TASKS";

    // Task/Note Status
    public static final int STATUS_UNCHECKED = 1;
    public static final int STATUS_CHECKED = 2;
    public static final int STATUS_DONE = 3;

    public static final class TasksColumns{
        public static final String ID = "_id";
        public static final String TITLE = "title";
        public static final String CATEGORY_NAME = "category_name";
        public static final String DUE_DATE = "due_date";
        public static final String PRIORITY = "priority";
        public static final String STATUS = "status";
    }

    public static final class NotesColumns{
        public static final String ID = "_id";
        public static final String TITLE = "title";
        public static final String PARENT_ID = "parent_task_id";
    }

    // Generate URI for a specific task's notes
    public static Uri getTaskNotesUri(Integer taskId){
        return Uri.withAppendedPath(TASKS_URI, taskId + "/notes");
    }

    // Any.do MIME types
    public static final String URI_TYPE_TASK_ITEM = "vnd.android.cursor.item/vnd.anydo.task";
    public static final String URI_TYPE_TASKS_DIR = "vnd.android.cursor.dir/vnd.anydo.task";
    public static final String URI_TYPE_NOTES_DIR = "vnd.android.cursor.dir/vnd.anydo.note";
}
}
