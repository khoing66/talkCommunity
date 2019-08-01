function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,content,1)

}

function comment2target(targetId,content,type) {
    if (!content) {
        alert("不能回复空消息。");
        return;
    }
    console.log(targetId);
    console.log(content);
    console.log(type);

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data:JSON.stringify({
            "parentId":targetId,
            "content": content,
            "type":type
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#comment_section").hide();
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=ed2253e1fd01b5b77b5a&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");;
                        window.localStorage("closable", true);
                    }

                } else {
                    alert(response.message);
                }
            }
        },

        dataType: "json"
    });
}

