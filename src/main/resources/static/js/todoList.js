'use strict';

let index = {
    init: function () {
        $("#list").on("keydown", (e) => {
            if(e.keyCode == 13) {
                this.save();
                $("#list").val("");
                $("#list").focus();
            }
        })
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
        // let id = $(".id").val();
        console.log(id)
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
        // let id = $("#id").val();
        let taskCompleted = index.checked;
        let data = {
            id: id,
            completed: taskCompleted,
        }
        console.log(id)
        $.ajax({
            type: "PUT",
            url: "/api/" + id,
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function (res) {
                alert("완료!");
                if (taskCompleted == false) {
                    index.previousElementSibling.classList.remove("on");
                    index.classList.remove("completedTrue");
                } else {
                    index.previousElementSibling.classList.add("on");
                    index.classList.add("completedTrue");
                }
                location.reload();
            },
            error: function (err) {
                alert(JSON.stringify(err));
            }
        });

    },

}
index.init();
