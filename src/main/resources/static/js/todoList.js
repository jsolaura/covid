'use strict';

let index = {
    init: function () {
        $("#saveBtn").on("click", () => {
            this.save();
            $(".content").show();
            console.log("???")

        })
        $("#list").on("keydown", (e) => {
            if(e.keyCode == 13) {
                this.save();
                $("#list").focus();
                $(".content").show();
                // e.preventDefault();
            }
        })

        // $(".deleteBtn").on("click", this.deleteById)
        // $(".completeBtn").forEach( function (btn, index) {
        //     btn.on("click", this.complete);
        // })
        // let completeBtn = $(".check");
        // for(let i=0; i<completeBtn.length; i++) {
        //     completeBtn[i].addEventListener("click", () => {
        //         this.completeById();
        //         console.log(this)
        //
        //     })
        // }
    },

    save: function () {
        let taskInput = $("#list").val();
        let data = {
            title: taskInput,
        }

        $.ajax({
            type: "POST",
            url: "/api",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function (res) {
                // alert("추가 완료!");
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
            },
            error: function (err) {
                alert(JSON.stringify(err));
            }
        });
    },

    completeById: function (el) {
        let id = $("#id").val();
        let taskCompleted = el;
        let data = {
            completed: taskCompleted,
        }

        if(taskCompleted === false) {
            $(".fa-check").removeClass("on");
        } else {
            $(".fa-check").addClass("on");
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
            },
            error: function (err) {
                alert(JSON.stringify(err));
            }
        })
    },

}
index.init();
