Rails.application.routes.draw do

  resources :resumes, only: [:index, :new, :create, :destroy]

  # resources :productlist do
  #    collection  {post :import}
  #  end

  post 'addlist', to: 'productlist#import'

  get 'allproducts', to: 'productlist#index'


  root "resumes#index"


 get 'saveproduct', to:'product#index'

 get 'savecsv', to: 'product#csv'

  get 'readfile', to:'file#index'

  get 'writefile', to: 'file#show'

  get 'readexcel', to: 'file#readexcel'

  get 'readpdf', to: 'file#readpdf'

  get 'readcsv', to: 'file#csv'




end
