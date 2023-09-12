let host = "http://localhost:8080"
$(document).ready(function () {
    const auth = getToken();
    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
        loadProfile();
    }
    loadFeed();
})
function getToken() {
    let auth = Cookies.get('Authorization');
    if (auth === undefined) {
        return '';
    }
    return auth;
}
function loadProfile() {
    $.ajax({
        url: "http://localhost:8080/api/accounts/profile/manage",
        method: 'GET',
        success: function (response) {
            $('#profile').empty();
            $('#profile').append(`
                <div class="feed">
                    <div><span>프로필</span></div>
                    <div class="image">
                        <img src="${response.imageUrl}">
                    </div>
                     <div class="feedContent">
                        <h4>${response.username}</h4>
                        <div class="description">
                            <p>${response.introduce}</p>
                        </div>
                    </div>
                    <button onclick="modifyProfile()">프로필 수정</button>
                </div>
            `)
        },
        error: function (error) {
            console.log('Error:', error);
        }
    })
}
function modifyProfile(){
    window.location.href = host + "/profile/manage"
}
function loadFeed() {
    $.ajax({
        url: 'http://localhost:8080/api/posts',
        method: 'GET',
        success: function (response) {
            console.log(response)
            $('#cards').empty();
            // 피드 카드 생성 및 추가
            response.forEach(feed => {
                let addHtml = addHTML(feed);
                $('#cards').append(addHtml)
            });
        },
        error: function (error) {
            console.log('Error:', error);
        }
    });
function addHTML(post) {
    return `<div class="card">
              <div class="post">
                <a class="feedHref" href="http://localhost:8080/api/posts/${post.id}">
                    <div class="image">
                        <img src="${post.imageUrl}" alt="post-img">
                    </div>
                </a>
                <div class="feedContent">
                    <a class="feedHref" href="http://localhost:8080/api/posts/${post.id}">
                        <h4>${post.title}</h4>
                        <div class="description">
                            <p>${post.content}</p>
                        </div>
                        <div> <p> 좋아요 개수: ${post.heartNum}</p></div>
                    </a>
                    <div class="subInfo">
                        <span> ${post.modifiedAt} </span>
                    </div>
                </div>
                <div class="feedUser">
                    <a class="userInfo" href="">
                        <span>
                            "by "
                            <b>${post.username}</b>
                        </span>
                    </a>
                </div>
                </div>
              </div>`
    }
}