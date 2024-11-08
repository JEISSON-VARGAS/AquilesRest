/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
      return [
        {
          source: '/api/:path*',
          destination: 'http://localhost:8081/api/:path*', // Proxy to Backend
        },
      ];
    },
  };
  
  export default nextConfig;
  