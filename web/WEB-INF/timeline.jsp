<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>

<html lang="en">
<head>
  <meta charset="utf-8">

  <title>Instatweet</title>
  <meta name="description" content="Instatweet">
  <meta name="author" content="Alessandro Caprarelli">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/main.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <!--[if lt IE 9]>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.js"></script>
  <![endif]-->
</head>

<body>


<div class="container">
  <div class="row">
    <div class="col-xs-12 well" style="margin-top: 15px; padding-bottom: 15px">
      <form id="send_post" accept-charset="UTF-8" action="${pageContext.request.contextPath}/post" method="POST">
              <textarea class="col-xs-12" id="new_message" name="new_message" placeholder="Type in your message" rows="5" style="margin-bottom: 5px"></textarea>
        <h6 class="pull-right">320 characters remaining</h6>
          <div class="col-xs-3">
              <button class="btn btn-info" type="submit">Post New Message</button>
          </div>
          <div class="col-xs-3">
              <input type="file" name="image" id="image">
          </div>
        <input type="text" hidden value="${username}" name="username">

      </form>
    </div>
  </div>
  <div class="row">

    <div class="timeline-centered">
      <c:set var="num_posts" value="${fn:length(posts)}" />
      <c:forEach var="i" begin="1" end="${num_posts}" step="1">
        <c:set var="post" value="${posts[num_posts-i]}" />
      <article class="timeline-entry">

        <div class="timeline-entry-inner">

          <div class="timeline-icon bg-success">
            <i class="entypo-feather"></i>
          </div>

          <div class="timeline-label">
            <h2 title="${post.time}"><a href=""><c:out value="${post.username}"/></a> <span>posted a status update</span></h2>
              <blockquote><c:out value="${post.text}"/></blockquote>
            <img style="display: none;" src="http://themes.laborator.co/neon/assets/images/timeline-image-3.png" class="img-responsive img-rounded full-width">

          </div>
        </div>

      </article>
      </c:forEach>

    </div>


  </div>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<script>
    $(document).ready(function() {
        // bind 'myForm' and provide a simple callback function
        $('#send_post').ajaxForm(function() {
            alert("Post added!");
        });
    });
</script>

</body>
</html>
