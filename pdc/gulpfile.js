var gulp = require('gulp');
var browserify = require('browserify');
var babelify = require('babelify');
var source = require('vinyl-source-stream');

gulp.task('build', function () {
    console.log("Recompiling " + Date.now());
    return browserify({entries: './app/dashboard/main.jsx', extensions: ['.jsx'], debug: true})
        .transform('babelify', {presets: ['es2015', 'react']})
        .bundle()
        .on('error', function(err) { console.error(err); this.emit('end'); })
        .pipe(source('main.js'))
        .pipe(gulp.dest('public'));
});

gulp.task('watch', ['build'], function () {
    gulp.watch('./app/dashboard/*.jsx', ['build']);
});

gulp.task('default', ['watch']);