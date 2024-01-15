import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import FooterComponent from './FooterComponent';

describe('FooterComponent', () => {
  it('renders footer with developer information', () => {
    render(<FooterComponent />);

    expect(screen.getByText('Developed By Ramón Bailén')).toBeInTheDocument();

    const footerElement = screen.getByRole('contentinfo');
    expect(footerElement).toHaveClass('footer');
  });
});