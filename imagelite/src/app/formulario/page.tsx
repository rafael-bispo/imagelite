'use client'

import { InputText, Template , Button, RenderIf, useNotification, FieldError, AuthenticatedPage} from '@/components'
import { useImageService } from '@/resources/image/image.service'
import { useFormik } from 'formik'
import React, { useState } from 'react';
import { FormProps, formScheme, formValidationScheme } from './formScheme'
import Link from 'next/link'

export default function FormularioPage(){

    const notification = useNotification();
    const [loading, setLoading] = useState<boolean>(false);
    const [imagePreview, SetImagePreview] = useState<string>();
    const service = useImageService();

    const formik = useFormik<FormProps>({
        initialValues: formScheme,
        onSubmit: handleSubmit,
        validationSchema: formValidationScheme
    })

    async function handleSubmit(dados: FormProps){

        setLoading(true);

        const formData = new FormData();
        formData.append("file", dados.file);
        formData.append("name", dados.name);
        formData.append("tags", dados.tags);


        await service.salvar(formData);

        formik.resetForm();
        SetImagePreview('');

        setLoading(false);

         
        notification.notify('Upload sent successfully!', 'success');
        
    }

    function onFileUpload(event: React.ChangeEvent<HTMLInputElement>){
        if(event.target.files){
            const file = event.target.files[0];
            //seta o arquivo no formik
            formik.setFieldValue('file', file);
            //criando url com o arquivo do upload para fazero preview
            const imageURL = URL.createObjectURL(file);
            //setando a image no preview
            SetImagePreview(imageURL);
        }
    }


    return(
        <AuthenticatedPage>
            <Template loading={loading}>
                <section className='flex flex-col items-center justify-center my-5'>
                    <h5 className='mt-3 mb-10 text-3xl font-extrabold tracking-tight text-slate-900 text-gray-900'>New Image</h5>
                    <form action="" onSubmit={formik.handleSubmit}>
                        <div className='grid grid-cols-1'>
                            <label className='block text-sm font-medium leading-6 text-gray-700'>Name: *</label>
                            <InputText id="name" value={formik.values.name} onChange={formik.handleChange} placeholder="type the image's name"/>
                            <FieldError error={formik.errors.name}/>
                        </div>
                        <div className='grid grid-cols-1 mt-5'>
                            <label className='block text-sm font-medium leading-6 text-gray-700'>Tags: *</label>
                            <InputText id='tags' onChange={formik.handleChange} placeholder="type the the tags comma separeted" value={formik.values.tags}/>
                            <span className='text-red-500'>
                                {formik.errors.tags}
                            </span>
                        </div>
                        <div className='grid grid-cols-1 mt-5'>
                            <label className='block text-sm font-medium leading-6 text-gray-700'>Image: *</label>
                            <span className='text-red-500'>
                                {formik.errors.file}
                            </span>
                            <div className='mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10'>
                                <div className='text-center'>
                                    <RenderIf condition={!imagePreview}>

                                        <svg 
                                            xmlns="http://www.w3.org/2000/svg" 
                                            fill="none" 
                                            viewBox="0 0 24 24" 
                                            strokeWidth={1.5} 
                                            stroke="currentColor" 
                                            className="w-12 h-12 text-gray-400 mx-auto" /* Tamanho e cor controlados pelo Tailwind */
                                        >
                                            <path 
                                                strokeLinecap="round" 
                                                strokeLinejoin="round" 
                                                d="m2.25 15.75 5.159-5.159a2.25 2.25 0 0 1 3.182 0l5.159 5.159m-1.5-1.5 1.409-1.409a2.25 2.25 0 0 1 3.182 0l2.909 2.909m-18 3.75h16.5a1.5 1.5 0 0 0 1.5-1.5V6a1.5 1.5 0 0 0-1.5-1.5H3.75A1.5 1.5 0 0 0 2.25 6v12a1.5 1.5 0 0 0 1.5 1.5Zm10.5-11.25h.008v.008h-.008V8.25Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z" 
                                            />
                                        </svg>
                                    </RenderIf>
                                    <div className='mt-4 flex text-sm leading-6 text-gray-600'>
                                        <label className='relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600'>
                                            <RenderIf condition={!imagePreview}>
                                                <span>Click to upload</span>
                                            </RenderIf>

                                            <RenderIf condition={!!imagePreview}>
                                                <img src={imagePreview} width={250} className='rounded-md'/>
                                            </RenderIf>

                                            <input onChange={onFileUpload} type="file" className='sr-only' />
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className='mt-5 flex items-center justify-end gap-x-6'>
                            <Button style='bg-blue-500 hover:bg-blue-300' label='Save' type='submit'/>
                            <Link href="/galeria">
                                <Button style='bg-red-500 hover:bg-red-300' label='Cancel' type='button'/>
                            </Link>
                        </div>
                    </form>
                </section>
            </Template>
        </AuthenticatedPage>        
    )
}