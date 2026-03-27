import Login from '@/app/login/page';
import { useAuth } from '@/resources'
import React from 'react'

interface AuthenticatedpageProps {
    children: React.ReactNode
}

export const AuthenticatedPage: React.FC<AuthenticatedpageProps> = ({ children }) => {

    const auth = useAuth();

    if(!auth.isSessionValid()){
        return <Login/>
    }

    return (
        <>
            {children}
        </>
    )
}