/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./WEB-INF/views/**/*.jsp",
    "./node_modules/flowbite/**/*.js",
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('flowbite/plugin'),
  ],
}
