'use client'

interface PrimeiroComponentProps{
    mensagem: string;
    mensagemConsole?: string; //a interrogação tira a orbigatoriedade deste atributo ao implementar a implementação desta interface
}

export const PrimeiroComponent: React.FC<PrimeiroComponentProps> = (props: PrimeiroComponentProps) => {
    
    function handleClick(){
        console.log(props.mensagemConsole)
    }
        
    return(
        <div>
            {props.mensagem}    

            <button onClick={handleClick}>Clique aqui</button>
        </div>
    )
}

export const ArrowFunction = () => {
    return (
        <h2>Arrow Function</h2>
    )
}