let host = "http://localhost:8080"
$(document).ready(function () {
    const auth = getToken();
    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
        // loadProfile();
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
// function signupAndLogin() {
//     var profile = document.getElementById('profile');
//     // var signupButton = document.createElement('button');
//     // var loginButton = document.createElement('button');
//     signupButton.textContent = 'SignUp';
//     loginButton.textContent = 'Login';
//
//     signupButton.addEventListener('click', function () {
//         window.location.href = "/signup"
//     });
//
//     loginButton.addEventListener('click', function () {
//         window.location.href = '/login';
//     });
//     profile.appendChild(signupButton);
//     profile.appendChild(loginButton);
// }
// function loadProfile() {
//     $.ajax({
//         url: "http://localhost:8080/api/accounts/settings-profile",
//         method: 'GET',
//         success: function (response) {
//             $('#profile').empty();
//                 $('#profile').append(`<div class="card">
//                                         <div><span>프로필</span></div>
//                                         <div class="profile-image">
//                                             <img src="${response.imageUrl}">
//                                         </div>
//                                          <div class="profile-contents">
//                                             <h4>${response.username}</h4>
//                                             <div class="introduce">
//                                                 <p>${response.introduce}</p>
//                                             </div>
//                                         </div>
//                                       </div>`)
//         },
//         error: function (error) {
//             console.log('Error:', error);
//         }
//     })
// }
function createPost() {
    window.location.href = host + "/posts"
}
function modifyProfile(){
    window.location.href = host + "/settings-profile"
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
function logout(){
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host;
}
// function follow(){
//     window.location.href = host + '/follows';
// }