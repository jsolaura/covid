'use strict';

let index = {
    init: function () {
        this.activeNum();
        this.displayControl();
        $("#list").on("keydown", (e) => {
            if (e.keyCode == 13) {
                this.save();
                $("#list").val("");
                $("#list").focus();
            }
        });
    },

    save: function () {
        let id = $("#id").val();
        let taskInput = $("#list").val();
        // let content = document.querySelector("content"+id);

        let data = {
            id: id,
            title: taskInput,
        }
        $.ajax({
            type: "POST",
            url: "/api",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function (res) {
                alert("추가 완료!");
                location.reload();

            },
            error: function (err) {
                alert(JSON.stringify(err));

            }
        });
    },

    deleteById: function (id) {
        $.ajax({
            type: "DELETE",
            url: "/api/" + id,
            success: function (res) {
                alert("삭제 완료!");
                location.reload();
            },
            error: function (err) {
                alert(JSON.stringify(err));
            }
        });
    },

    completeById: function (id, index) {
        let taskCompleted = index.checked == true;
        let data = {
            id: id,
            completed: taskCompleted,
        }
        console.log(id , taskCompleted)
        $.ajax({
            type: "PUT",
            url: "/api/" + id,
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function (res) {
                alert("완료!");
                if (taskCompleted) {
                    index.previousElementSibling.classList.add("on");
                    index.classList.add("completedTrue");
                    return !taskCompleted;
                } else if (!taskCompleted) {
                    index.previousElementSibling.classList.remove("on");
                    index.classList.remove("completedTrue");
                }
                location.reload();
            },
            error: function (err) {
                alert(JSON.stringify(err));
            }
        });
    },

    activeNum: function () {
        const num = $(".activeNum");
        let task = $(".off").length;
        num.html(task);
    },

    displayControl: function () {
        const taskList = $(".task");
        let list = taskList.find(".on").parent();
        let hideList = taskList.find(".off").parent();

        $(".all").on("click", () => {
            hideList.show();
            list.show();
        })

        $(".activeBtn").on("click", () => {
            if(taskList.children()) {
                let list = taskList.find(".off").parent();
                let hideList = taskList.find(".on").parent();
                hideList.hide();
                list.show();
            } else {
                alert("Everything were clear! \n Make more stuff!")
            }
        })
        $(".completeBtn").on("click", () => {
            if(taskList.children()) {
                hideList.hide();
                list.show();
            } else {
                alert("Have something! \n Do some stuff!")
            }
        })
    },

}
index.init();
