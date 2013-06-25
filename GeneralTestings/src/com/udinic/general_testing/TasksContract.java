package com.udinic.general_testing;

import android.net.Uri;

/**
 * This class provides the URI and const values to work with Any.do's Content Provider.
 *
 * A guide is available on http://tech.any.do/content-provider-for-any-do/
 */
public class TasksContract {
    public static final Uri TASKS_URI = Uri.parse("content://com.anydo.provider/tasks");
    public static final Uri FOLDERS_URI = Uri.parse("content://com.anydo.provider/folders");

    public static final String PERMISSION_READ = "com.anydo.provider.permission.READ_ANYDO_TASKS";

    // Broadcast intent that is sent when the task list has been refreshed
    public static final String INTENT_ACTION_TASKS_REFREHSED = "com.anydo.intent.INTENT_ACTION_TASKS_REFRESHED";

    // Task/Note Status
    public static final int STATUS_UNCHECKED = 1;
    public static final int STATUS_CHECKED = 2;
    public static final int STATUS_DONE = 3;

    public static final class FolderColumns{
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String IS_DEFAULT = "is_default";

    }
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
        public static final String STATUS = "status";
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