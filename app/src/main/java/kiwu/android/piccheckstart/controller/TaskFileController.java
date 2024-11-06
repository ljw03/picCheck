package kiwu.android.piccheckstart.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import kiwu.android.piccheckstart.model.TaskModel;

public class TaskFileController {
    private static final String FILE_NAME = "tasks.txt";
    private static final String SEPERATOR = "\t";
    private final File file;

    public TaskFileController(String path) throws IOException {
        file = new File(path, FILE_NAME);

        if(! file.exists()) {
            file.createNewFile();
        } // end if~else
    } // end constructor

    public ArrayList<TaskModel> read() throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));

        ArrayList<TaskModel> tasks = new ArrayList<>();
        TaskModel task = null;
        String line = null;
        StringTokenizer tokens = null;

        while((line = in.readLine()) != null) {
            tokens = new StringTokenizer(line, SEPERATOR);

            task = new TaskModel(tokens.nextToken());
            task.setTitle(tokens.nextToken());
            task.setPicture(tokens.nextToken());
            task.setCreateDateStr(tokens.nextToken());

            tasks.add(task);
        } // end while

        System.out.println("> " + tasks.size() + "개의 투두리스트를 파일에서 읽었습니다.");
        in.close();

        return tasks;
    } // end read

    public void write(ArrayList<TaskModel> tasks)throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        if(tasks == null || tasks.isEmpty()) {
            out.write("");
            return;
        } // end if

        Iterator<TaskModel> iterator = tasks.iterator();

        TaskModel task = null;
        String line = null;
        while (iterator.hasNext()) {
            task = iterator.next();

            line = task.getList_id() + SEPERATOR +
                    task.getTitle() + SEPERATOR +
                    task.getDate() + SEPERATOR +
                    task.getTime() + SEPERATOR +
                    task.getCategory();

            out.write(line);
            out.newLine();
        } // end while

        System.out.println("> " + tasks.size() + "건의 학생정보를 파일에 저장했습니다.");
        out.flush();
        out.close();
    } // end write

    public void create(TaskModel task) throws IOException {
        ArrayList<TaskModel> tasks = read();

        if(tasks == null) {
            tasks = new ArrayList<>();
        } // end if

        if(tasks.contains(task)) {
            throw new IOException("[" + task.getTitle() + "] 학생이 이미 존재합니다.");
        } else {
            tasks.add(task);

            write(tasks);
        } // end if~else
    }

}
