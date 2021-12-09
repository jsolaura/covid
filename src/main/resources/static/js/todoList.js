'use strict';

let index = {
    init: function () {
        $("#list").on("keydown", (e) => {
            if(e.keyCode == 13) {
                this.save();
            }
        })
        $("#deleteBtn").on("click", () => {
            this.deleteById();
        })

        $("#completeBtn").on("click", () => {
            this.complete();
        })

    },

    save: function () {
        let data = {
            title: $("#list").val(),
        }

        $.ajax({
            type: "POST",
            url: "/api",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (res) {
                alert("추가 완료!");
            },
            error: function (err) {
                console.log(err);
            }

        })
    },

    deleteById: function () {
        let id = $("#id").val();
        $.ajax({
            type: "DELETE",
            url: "/api" + id,
            dataType: "json",
            success: function (res) {
                alert("삭제 완료!");
            },
            error: function (err) {
                console.log(err);
            }
        })

    },

    complete: function () {
        let id = $("#id").val();
        let data = $("#completeBtn").val();
        $(".fa-check").toggleClass("active");

        $.ajax({
            type: "PUT",
            data: JSON.stringify(data),
            url: "/api" + id,
            dataType: "json",
            success: function (res) {
                alert("완료!");
            },
            error: function (err) {
                console.log(err);
            }
        })
    },

    // toggle: function () {
    //     let change = false;
    //     if(change) {
    //
    //     }
    // }
}
index.init();