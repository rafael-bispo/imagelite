'use client'

import {Template, ImageCard, Button, InputText, useNotification, AuthenticatedPage} from '@/components'
import { Image } from '@/resources/image/image.resource';
import {useImageService} from '@/resources'
import {useState} from 'react'
import Link from 'next/link';

export default function GaleriaPage(){
    
    const useService =  useImageService();
    const notification = useNotification();
    const [images, setImages] = useState<Image[]>([]);
    const [query, setQuery] = useState<string>('');
    const [extension, setExtension] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(false)

    async function searchImages(){
        setLoading(true);
        const result = await useService.buscar(query, extension);
        setImages(result);
        console.table(result);
        setLoading(false);

        if(!result.length){
            notification.notify('No results found!', 'warning');
        }
    }

    function renderImageCard(image: Image){
        return(
            <ImageCard key={image.url} nome={image.name} src={image.url} tamanho={image.size} dataUpload={image.uploadDate} extension={image.extension}/>
        )
    }

    function renderImgCards(){
        return images.map(renderImageCard);
    }

    
    return(
        <AuthenticatedPage>
            <Template loading={loading}>            
                <section className='flex flex-col items-center justify-center my-5'>
                    <div className='flex space-x-4'>

                        <InputText onChange={event => setQuery(event.target.value)} placeholder="Type Name or Tag of Images"/>

                        <select onChange={event => setExtension(event.target.value)} className='order px-4 py-2 rounded-lg text-gray-900' name="" id="">
                            <option value="">All formats</option>
                            <option value="PNG">PNG</option>
                            <option value="GIF">GIF</option>
                            <option value="JPEG">JPG</option>
                        </select>
                        <Button onClick={searchImages} style={"bg-blue-500 hover:bg-blue-300"} label='Search'/>

                        <Link href={"/formulario"}>
                            <Button style={"bg-yellow-500 hover:bg-yellow-300"} label='Add New'/>
                        </Link>
                        
                    </div>
                </section>

                <section className='grid grid-cols-3 gap-8'>
                    {
                        renderImgCards()
                    }
                </section>
            </Template>
        </AuthenticatedPage>
    )
}