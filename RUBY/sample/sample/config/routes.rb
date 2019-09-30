Rails.application.routes.draw do
  resources :categories
  resources :comments
  resources :articles
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  resources :users, except:[:new]

  get 'signup', to:'users#new'

  root  'welcome#home'

  get 'about', to:'welcome#about'

  get 'login', to:'session#new'
  post 'login', to:'session#create'
  delete 'logout', to:'session#destory'

  get 'readmail', to: 'mail#readmail'
end
