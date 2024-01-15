import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import HeaderComponent from './HeaderComponent';

describe('HeaderComponent', () => {
  it('renders header with correct title', () => {
    render(<HeaderComponent />);

    expect(screen.getByText('Course Management App')).toBeInTheDocument();
  });
});
