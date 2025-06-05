import React, { FC, useState, useEffect } from 'react';
import {
  Box,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  IconButton,
  Typography
} from '@mui/material';
import HomeIcon from '@mui/icons-material/Home';
import PersonIcon from '@mui/icons-material/Person';
import EventIcon from '@mui/icons-material/Event';
import BookOnlineIcon from '@mui/icons-material/BookOnline';
import LogoutIcon from '@mui/icons-material/Logout';
import MenuIcon from '@mui/icons-material/Menu';
import { useSelector } from 'react-redux';
import { useLogoutMutation } from '../../services/auth.service';
import { selectAuthSlice } from '../../state/auth/slice';
import { navigate } from "gatsby";

const drawerWidth = 240;

const Sidebar: FC = () => {
  const [open, setOpen] = useState(true);
  const [requestLogout, result] = useLogoutMutation();
  const { userInfo } = useSelector((state: any) => selectAuthSlice(state));
 
   const handleNavigation = (path: string) => {
    navigate(path);
  };

  const toggleDrawer = () => {
    setOpen(!open);
  };

  const performLogout = () => {
    requestLogout();
  };

  useEffect(() => {
    if (result.isSuccess) {
      navigate('/login');
    }
  }, [result, navigate]);

  const menuItems = [
    { 
      text: 'Home', 
      icon: <HomeIcon />, 
      action: () => handleNavigation('/home')
    },
    { 
      text: 'Profile', 
      icon: <PersonIcon />, 
      action: () => handleNavigation('/profile')
    },
    { 
      text: 'Session', 
      icon: <EventIcon />, 
      action: () => handleNavigation('/session')
    },
    { 
      text: 'Booking', 
      icon: <BookOnlineIcon />, 
      action: () => handleNavigation('/booking')
    },
    { 
      text: 'Logout', 
      icon: <LogoutIcon />, 
      action: performLogout 
    },
  ];


  return (
    <>
      <IconButton
        onClick={toggleDrawer}
        sx={{ position: 'fixed', top: 16, left: 16, zIndex: 1300 }}
        color="inherit"
      >
        <MenuIcon />
      </IconButton>

      <Drawer
        variant="persistent"
        open={open}
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
            pt: '20px',
          },
        }}
      >
        <Toolbar />
        <Box sx={{ px: 2 }}>
          <Typography variant="h6" sx={{ mb: 2 }}>
            Welcome, {userInfo?.user_name}!
          </Typography>
        </Box>
        <List>
          {menuItems.map((item) => (
            <ListItem key={item.text} disablePadding>
              <ListItemButton onClick={item.action || undefined}>
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.text} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      </Drawer>
    </>
  );
};

export default Sidebar;