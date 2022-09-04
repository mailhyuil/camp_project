/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./WEB-INF/views/**/*.html",
    "./node_modules/flowbite/**/*.js",
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('flowbite/plugin'),
  ],
}
