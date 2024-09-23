import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Dashboard from './Dashboard';
import Team from './Team';

const App = () => {
  return (
      <Router>
        <div style={{ backgroundColor: '#1a1a1a', minHeight: '100vh' }}>
          <nav style={{ padding: '20px' }}>
            <Link style={{ color: 'yellow', marginRight: '10px' }} to="/">Dashboard</Link>
            <Link style={{ color: 'yellow' }} to="/team">Team</Link>
          </nav>
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/team" element={<Team />} />
          </Routes>
        </div>
      </Router>
  );
};

export default App;
