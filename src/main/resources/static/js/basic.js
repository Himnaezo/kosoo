let host = "http://localhost:8080";

$(document).ready(function () {
    const auth = getToken();
    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
        loadProfile();
    }
    loadFeed();
});

function getToken() {
    let auth = Cookies.get('Authorization');
    if (auth === undefined) {
        return '';
    }
    return auth;
}

function loadProfile() {
    // 로그인 상태에서만 프로필 로드
    if (getToken()) {
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
        });
    }
}

function modifyProfile() {
    const auth = getToken();
    if (auth) {
        window.location.href = host + "/profile/manage";
    } else {
        alert('로그인 후에 프로필을 수정할 수 있습니다.');
    }
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
}

function addHTML(post) {
    return `<div class="col">
                <a href="http://localhost:8080/posts/${post.id}" class="card card-post text-bg-dark position-relative">
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
            </div>`;
}

function logout() {
    const auth = getToken();

    // 이미 로그아웃된 경우 알림 후 리다이렉트
    if (!auth) {
        alert('이미 로그아웃 상태입니다.');
        window.location.href = '/';
        return;
    }

    fetch('/api/accounts/logout', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': auth, // Authorization 헤더 추가
        },
    })
        .then(response => {
            if (response.status === 200) {
                window.location.href = '/';
                alert('로그아웃 성공');
            } else {
                alert('로그아웃 실패');
                console.error('로그아웃 실패');
            }
        })
        .catch(error => {
            alert('로그아웃 중 오류 발생');
            console.error('로그아웃 중 오류 발생', error);
        });
}
// "/profile"로 접근 시 로그인하지 않은 경우 알림 후 리다이렉트
if (window.location.pathname === '/profile') {
    if (!getToken()) {
        alert('로그인 후 사용 가능합니다.');
        window.location.href = '/';
    }
}
