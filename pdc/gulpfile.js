var gulp = require("gulp");
var browserify = require("browserify");
var reactify = require("reactify");
var source = require("vinyl-source-stream");

gulp.task("bundle", function () {
    return browserify({
        entries: "./app/dashboard/main.jsx",
        debug: true
    }).transform(reactify)
        .bundle()
        .pipe(source("main.js"))
        .pipe(gulp.dest("public"))
});

gulp.task("copy", ["bundle"], function () {
    return gulp.src(["app/dashboard/index.html","lib/bootstrap-css/css/bootstrap.min.css","app/dashboard/style.css"])
        .pipe(gulp.dest("public"));
});

gulp.task("default",["copy"],function(){
   console.log("Gulp completed..."); 
});