import { useState } from 'react';
import Sidebar from '../components/landing/Sidebar';
import Navbar from '../components/landing/Navbar';
import HeroSection from '../components/landing/HeroSection';
import AboutSection from '../components/landing/AboutSection';
import TestimonialsSection from '../components/landing/TestimonialsSection';
import TeamSection from '../components/landing/TeamSection';
import Footer from '../components/landing/Footer';
import TariffSection from '../components/landing/TariffSection';

function Home() {
    const [isOpen, setIsOpen] = useState(false);
    const toggle = () => {
        setIsOpen(!isOpen);
    };

    return (
        <>
            <Sidebar isOpen={isOpen} toggle={toggle} />
            <Navbar toggle={toggle} />
            <HeroSection />
            <AboutSection />
            <TestimonialsSection />
            <TariffSection />
            <TeamSection />
            <Footer />
        </>
    );
}

export default Home;
