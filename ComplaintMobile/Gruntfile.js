'use strict';

module.exports = function(grunt) {

  // Load grunt tasks automatically
  require('load-grunt-tasks')(grunt);

  // Project configuration.
  grunt.initConfig({

    // Project settings
    complaints: {
      // configurable paths
      app:  'WebApp',
      targetDir: 'Grumbl/www'
    },
    //ensures top quality JS
    jshint: {
      options: {
    	  globals: {
              angular: true,
              jQuery: true,
              console: true,
              module: true
            },
        reporter: require('jshint-stylish')
      },
      all: ['<%= complaints.app %>/complaints/**/*.js']
    },
    //converts the html file to only show the minifed JS
  	processhtml: {
  		main:{
  		options:{
            process: true,
        },
  	    files: {
  	      '<%= complaints.targetDir %>/index.html' : '<%= complaints.app %>/index.html'
  	    }
  	  }
  	},

    copy: {
      main: {
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= complaints.app %>',
          dest: '<%= complaints.targetDir %>',
          src: [
            //Files to Copy - will need to copy most from web to www    
            '**/*',
            '!temp/**',
            '!WEB-INF/**',
            '!complaints/**/js/**',
            '!complaints/app.js',
            '!complaints/authInterceptor.js',
            '!complaints/watchHeight.js',
            '!lib/ionic/**/*',
            'lib/ionic/css/ionic.min.css',
            'lib/ionic/fonts/**/*',
            '!index.html'
          ]
        }]
      }
    },
 // Concat files
    concat: {
         options: {
           // define a string to put between each file in the concatenated output
           separator: '\n/*js file*/\n'
         },
         main: {
           // the files to concatenate
           src: [
                 '<%= complaints.app %>/complaints/**/*.js'
                 ],
           // the location of the resulting JS file
           dest: '<%= complaints.app %>/temp/concat.js'
         }
       },
       
     	replace: {
     	  	  main: {
     	  	    src: ['<%= complaints.app %>/temp/concat.js'],
     	  	    overwrite: true,                 // overwrite matched source files
     	  	    replacements: [{
     	  	      from: "localhost:8080/ComplaintService",
     	  	      to: "default-environment-4y3vry6tx2.elasticbeanstalk.com"
     	  	    }]
     	  	  }
     	  	},
  	
  	//grunt uglify
  	 uglify: {
  		main: {
  	      files: {
  	    	'<%= complaints.targetDir %>/complaints.min.js': '<%= complaints.app %>/temp/concat.js'
  	      }
  	    }
  	  },
  	  
  	clean: {
  	  js: ["<%= complaints.targetDir %>/**/*"]
  	}
  });

  grunt.registerTask('test', function () {
	    grunt.task.run(['jshint']);
  });
  
  grunt.registerTask('build', function () {
    grunt.task.run(['jshint','clean','concat','replace','uglify','processhtml','copy']);
  });

};