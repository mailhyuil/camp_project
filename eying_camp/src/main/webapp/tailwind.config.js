/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./WEB-INF/views/**/*.html",
    "./node_modules/flowbite/**/*.js",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#1A56DB'
      },
      fontFamily: {
        logo: ['Pacifico'],
        main: ['Noto Sans KR']
      },
    },
  },
  plugins: [
    require('flowbite/plugin'),
  ],
}
