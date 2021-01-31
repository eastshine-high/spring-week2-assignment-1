package com.codesoom.assignment.controllers;

import com.codesoom.assignment.models.Task;
import com.codesoom.assignment.models.TaskRequestForm;
import com.codesoom.assignment.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 할 일과 관련된 HTTP 요청 처리를 담당합니다.
 *
 * @see TaskService
 */
@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    /**
     * 모든 할일을 리턴합니다.
     */
    @GetMapping
    public List<Task> list() {
        return taskService.getTasks();
    }

    /**
     * 주어진 할 일을 저장한 뒤, 저장된 할 일을 리턴합니다.
     * 
     * @param taskRequestForm 저장하고자 하는 할 일
     * @return 저장된 할 일
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@RequestBody @Valid TaskRequestForm taskRequestForm) {
        Task task = modelMapper.map(taskRequestForm, Task.class);
        return taskService.addTask(task);
    }
    
    /**
     * 주어진 id에 해당하는 할 일을 찾아 리턴합니다.
     *
     * @param id 찾고자 하는 할 일의 id
     * @return 찾은 할 일
     */
    @GetMapping("/{id}")
    public Task read(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    /**
     * 주어진 id에 해당하는 할 일을 찾아 수정하고 수정된 할 일을 리턴합니다.
     *
     * @param id 수정하고자 하는 할 일의 id
     * @param taskRequestForm 수정하고자 하는 할 일
     * @return 수정된 할 일
     */
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody @Valid TaskRequestForm taskRequestForm) {
        Task task = modelMapper.map(taskRequestForm, Task.class);
        return taskService.updateTask(id, task);
    }

    /**
     * 주어진 id에 해당하는 할 일을 찾아 삭제합니다.
     *
     * @param id 삭제하고자 하는 할 일의 id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
