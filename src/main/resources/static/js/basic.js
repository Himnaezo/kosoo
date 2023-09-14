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
              <div class="card" style="width: 20rem;">
                <img src="${response.imageUrl}" class="image card-img-top" alt="프로필사진">
                 <div class="feedContent card-body">
                    <h4>${response.username}</h4>
                    <div class="description card-text">
                        <p>${response.introduce}</p>
                    </div>
                </div>
                <div class="d-grid gap-2 d-md-flex justify-content-md-end mb-3 me-3">
                  <button type="button" class="btn btn-submit" onclick="modifyProfile()">프로필 수정</button>
                </div>
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

    return `<div class="col">
                <a href="http://localhost:8080/api/posts/${post.id}" class="card card-post text-bg-dark position-relative">
                  <img src="${post.imageUrl}" class="card-img overflow-hidden" alt="게시글썸네일">

                  <div class="card-img-overlay">
                    <div class="card-title fs-4 fw-bold m-3">${post.title}</div>

                    <div class="card-text text-end fs-6 position-absolute bottom-0 end-0 m-3">
                      <span class="text-start fw-bold">${post.username}</span>
                      <span class="ms-2"><i class="fa-solid fa-heart fa-beat fa ms-2" style="color: #A593E0;"></i> ${post.heartNum}</span>
                      <div class=""><small>${post.modifiedAt}</small></div>
                    </div>
                  </div>
                </a>
            </div>`
    }
}